# Sea Discovery Alert System
# This program sends an alert text message and email to a pre-designated email address

import smtplib
import sys

sender_email = "seadiscoveryalertsystem@gmail.com"
email_password = "Fish123++"

#m = sys.argv[1]
#sensor_id = sys.argv[1]
#sensor_name = sys.argv[2]
#sensor_temp = sys.argv[3]

# creates SMTP session
s = smtplib.SMTP('smtp.gmail.com', 587)

# start TLS for security
s.starttls()

# Authentication
s.login(sender_email, email_password)

# Raspberry """ + sensor_id + """ has detected that tank """ + sensor_name + """ has reached critical levels at """ + sensor_temp + """ degrees!"""
# message to be sent


email = ""
outstr = ["Warning. System detected temperature out of range for: \n"]
for i in range(len(sys.argv)):

    if(i == (len(sys.argv)-1)):
        email = sys.argv[i]
	
    elif (i >= 1):
	        outstr += " " +sys.argv[i]


separator = ''
print(separator.join(outstr))

message = """\
Subject: Fish Broadcast Alert

""" + separator.join(outstr) + """!"""


# sending the mail
try:
    print("Attempting to send email...")
    s.sendmail(sender_email, email, message)
    #s.sendmail(sender_email, "13608200132@tmomail.net", message)
    #s.sendmail(sender_email, "14252197381@tmomail.net", message)

except NameError:
    print(" Failure!")
else:
    print(" Success!")

# terminating the session
s.quit()
