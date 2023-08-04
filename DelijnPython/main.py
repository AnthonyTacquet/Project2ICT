import pysftp
import paramiko
import shutil
import zipfile
import os
import shutil
import sys

host = "transfer.delijn.be"
user = "TAP-SFTP-GTFS00109"
port = 22
password = "fu1YNqI7yBHBxo4HqW66"

remotepath = "/Public/gtfs_transit.zip"
localpath = "./gtfs_transit.zip"
dirextractpath = "./extracted"

files = ["agency.txt", "calendar_dates.txt", "feed_info.txt", "routes.txt", "shapes.txt", "stops.txt", "stop_times.txt", "trips.txt"]

cnopts = pysftp.CnOpts()
cnopts.hostkeys = None
cnopts.log = True

# check given directory
if len(sys.argv) != 2:
	sys.exit("Give output directory as argument")

givendir = sys.argv[1]

if os.path.isdir(givendir):
	dirextractpath = givendir
else:
	sys.exit("Invalid output directory")

# Download from ftp server
with pysftp.Connection(host=host, port=port, username=user, password=password, cnopts=cnopts) as sftp:
	sftp_file = sftp.open(remotepath, 'rb')
	with open(localpath, 'wb') as out_file:
		shutil.copyfileobj(sftp_file, out_file)
		sftp_file.close()
	sftp.close()

# Extract zip to extracted directory
with zipfile.ZipFile(localpath, 'r') as zip_ref:
    zip_ref.extractall(dirextractpath)

# Remove zip and make extracted directory empty
#for filename in files:
#	os.remove(dirextractpath + '/' + filename)
os.remove(localpath)
print("Done")
