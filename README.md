# Our project
app:
* branch: development
* folder: Aion2

video:
* branch: main
* folder: root

# 2223-PROJVertrekalarm

nmbs raw data:
https://www.belgiantrain.be/nl/3rd-party-services/mobility-service-providers/public-data/use-our-data

nmbs docs api:
https://docs.irail.be/

delijn docs api:
https://data.delijn.be/docs/services/de-lijn-gtfs-v2/operations/get-getgtfs

Aan Anthony vragen voor delijn api keys :)

# mysql settings:
command read local file and write to db:
SET GLOBAL local_infile=1;

OR

sudo nano /etc/mysql/my.cfg

and add:

```
[mysqld]
local-infile 

[mysql]
local-infile 
```

# ServerSideDeLijn
java -jar Main.jar &
=> returns process number
kill process:
=> kill <process number> 

=> ps aux | grep Main.jar
   And kill <returned process>

# Run python script at midnight
crontab -u ubuntu -e
 - opens nano editor
0 0 * * * python3 /opt/python/main.py /opt/python/
 - this command will run a python script everyday at midnight

 - install postfix to remove possible errors when crontab is running
```sudo apt-get install postfix```

# Run python script by yourself
python3 /opt/python/main.py /opt/python/
