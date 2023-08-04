import pysftp
import paramiko
import shutil
import zipfile
import os
import shutil
import sys
import mysql.connector
from mysql.connector import Error
from datetime import datetime

host = "transfer.delijn.be"
port = 22
user = "TAP-SFTP-GTFS00109"
password = "fu1YNqI7yBHBxo4HqW66"

dbhost = "con001.tacquet.be"
dbport = 60001
dbdb = "delijn"
dbuser = "delijn"
dbpassword = "7tSYWQdURhrX69wsNQPm"

remotepath = "/Public/gtfs_transit.zip"

# check given directory
if len(sys.argv) != 2:
        givendir = "./"
else:
        givendir = sys.argv[1]

if not givendir.endswith('/'):
    givendir = givendir+'/'

files = ["agency.txt", "calendar_dates.txt", "feed_info.txt", "routes.txt", "shapes.txt", "stops.txt", "stop_times.txt", "trips.txt"]

cnopts = pysftp.CnOpts()
cnopts.hostkeys = None
cnopts.log = True

def ftp_download():
        global givendir

        print("Starting download from ftp server")
        # Download from ftp server
        with pysftp.Connection(host=host, port=port, username=user, password=password, cnopts=cnopts) as sftp:
                sftp_file = sftp.open(remotepath, 'rb')
                with open(givendir + "gtfs_transit.zip", 'wb') as out_file:
                        shutil.copyfileobj(sftp_file, out_file)
                        sftp_file.close()
                sftp.close()

        # Extract zip to extracted directory
        with zipfile.ZipFile(givendir + "gtfs_transit.zip", 'r') as zip_ref:
            zip_ref.extractall(givendir)

        print("Done")

def to_database():
        try:
                connection = mysql.connector.connect(host=dbhost, port=dbport, database=dbdb, user=dbuser, password=dbpassword, allow_local_infile=True)
                if connection.is_connected():
                        db_Info = connection.get_server_info()
                        print("Connected to MySQL Server version: ", db_Info)
                        cursor = connection.cursor()
                        cursor.execute("select database();")
                        record = str(cursor.fetchone()[0])
                        print("Connected to MySQL Database: ", record)
                        print("Importing files:")

                        cursor.execute("SET FOREIGN_KEY_CHECKS=0;")

                        print(" > stops")
                        cursor.execute("DELETE FROM " + record + ".stops;")
                        cursor.execute("LOAD DATA LOCAL INFILE '" + givendir + "stops.txt" + "' INTO TABLE " + record + ".stops FIELDS TERMINATED BY ',' ENCLOSED BY '\"' LINES TERMINATED BY '\\n' IGNORE 1 ROWS;")

                        print(" > feed_info")
                        cursor.execute("DELETE FROM " + record + ".feed_info;")
                        cursor.execute("LOAD DATA LOCAL INFILE '" + givendir + "feed_info.txt" + "' INTO TABLE " + record + ".feed_info FIELDS TERMINATED BY ',' ENCLOSED BY '\"' LINES TERMINATED BY '\\n' IGNORE 1 ROWS;")

                        print(" > calendar_dates")
                        cursor.execute("DELETE FROM " + record + ".calendar_dates;")
                        cursor.execute("LOAD DATA LOCAL INFILE '" + givendir + "calendar_dates.txt" + "' INTO TABLE " + record + ".calendar_dates FIELDS TERMINATED BY ',' ENCLOSED BY '\"' LINES TERMINATED BY '\\n' IGNORE 1 ROWS;")

                        print(" > agency")
                        cursor.execute("DELETE FROM " + record + ".agencies;")
                        cursor.execute("LOAD DATA LOCAL INFILE '" + givendir + "agency.txt" + "' INTO TABLE " + record + ".agencies FIELDS TERMINATED BY ',' ENCLOSED BY '\"' LINES TERMINATED BY '\\n' IGNORE 1 ROWS;")

                        print(" > routes")
                        cursor.execute("DELETE FROM " + record + ".routes;")
                        cursor.execute("LOAD DATA LOCAL INFILE '" + givendir + "routes.txt" + "' INTO TABLE " + record + ".routes FIELDS TERMINATED BY ',' ENCLOSED BY '\"' LINES TERMINATED BY '\\n' IGNORE 1 ROWS;")

                        print(" > shapes")
                        cursor.execute("DELETE FROM " + record + ".shapes;")
                        cursor.execute("LOAD DATA LOCAL INFILE '" + givendir + "shapes.txt" + "' INTO TABLE " + record + ".shapes FIELDS TERMINATED BY ',' ENCLOSED BY '\"' LINES TERMINATED BY '\\n' IGNORE 1 ROWS;")

                        print(" > trips")
                        cursor.execute("DELETE FROM " + record + ".trips;")
                        cursor.execute("LOAD DATA LOCAL INFILE '" + givendir + "trips.txt" + "' INTO TABLE " + record + ".trips FIELDS TERMINATED BY ',' ENCLOSED BY '\"' LINES TERMINATED BY '\\n' IGNORE 1 ROWS;")

                        print(" > stop_times")
                        cursor.execute("DELETE FROM " + record + ".stop_times;")
                        cursor.execute("LOAD DATA LOCAL INFILE '" + givendir + "stop_times.txt" + "' INTO TABLE " + record + ".stop_times FIELDS TERMINATED BY ',' ENCLOSED BY '\"' LINES TERMINATED BY '\\n' IGNORE 1 ROWS;")

        except Error as e:
                print("Not connected to MySQL", e)
        finally:
                if connection.is_connected():
                        print("Commiting")
                        connection.commit()
                        cursor.execute("SET FOREIGN_KEY_CHECKS=1;")
                        cursor.close()
                        connection.close()
                        print("MySQL connection is closed")


def remove_files():
        # Remove zip and make extracted directory empty
        print("Removing files")
        for filename in files:
                os.remove(givendir + filename)
        os.remove(givendir + "gtfs_transit.zip")
        print("Done")

def write_log(text):
        now = datetime.now()
        dt_string = now.strftime("%d/%m/%Y %H:%M:%S")
        string = "echo '" + dt_string + " UTC\t" + text + "'"  + " >> " + givendir + "log.txt"
        print(string)
        os.system(string)

def main():
        write_log("Started updating")
        ftp_download()
        to_database()
        remove_files()
        write_log("Finished updating")

if __name__ == "__main__":
        main()
