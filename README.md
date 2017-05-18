
# csv-event-producer

This is a simple csv event producer which produce csv lines and writes them to socket line by line.
There're 11 product categories with at least 12 products in each.    
IP address is generated randomly.    
Date of purchase is generated randomly in range \<date-from> <date-to>.   
Every line takes random product and creates a purchase which is sent to socket as a CSV line. 

```
usage: csv-event-producer [-e <events-num>] -f <date-from> [-h <host>]
       [--help] [-p <port>] [-T <total-events-num>] -t <date-to>
 -e,--events-per-sec <events-num>       How many events per second must be
                                        produced. Default is 100
 -f,--date-from <date-from>             REQUIRED. The start date in the
                                        range in which the events will be
                                        created. Format of date:
                                        yyyy-MM-dd HH:mm:ss
 -h,--host <host>                       Host to which events must be sent.
                                        Default is 127.0.0.1
    --help                              Prints this message
 -p,--port <port>                       Port to which events must be sent.
                                        Default is 9999
 -T,--total-events <total-events-num>   Total events number which must be
                                        generated. If not specified,
                                        events will be produced endlessly.
 -t,--date-to <date-to>                 REQUIRED. The end date in the
                                        range in which the events will be
                                        created. Format of date:
                                        yyyy-MM-dd HH:mm:ss
```

Example: `java -jar csv-event-producer-1.0-jar-with-dependencies.jar -f "2017-04-01 00:00:00" -t "2017-04-08 23:59:59" -T 150 -p 9999 -h 127.0.0.1 -e 10
`

Output example:
```
Samsung Galaxy S8	          599.0	  2017-04-08 12:06:36	Smartphones	 229.110.79.175
Case for iPhone 7	          69.0	  2017-04-03 13:07:05	Accessories	 83.59.89.175
EM4 Educational Motorized Robot	  259.0	  2017-04-05 16:50:27	Toys	         104.220.164.11
Leica X2	                  1209.0  2017-04-01 18:31:24	Cameras	         156.199.144.190
Case for Samsung Galaxy S8 Plus   99.0	  2017-04-01 14:23:37	Accessories	 203.55.231.234
Anker PowerCore 10k	          149.0	  2017-04-04 03:59:23	Power Banks	 58.171.241.224
Asus Zen Pad 3S	                  499.0	  2017-04-07 06:50:50	Tablets	         120.46.44.64
GoPro Silver 3	                  199.0	  2017-04-04 04:26:23	Action Cameras	 206.68.205.77
TP-Link 4	                  299.0	  2017-04-08 20:55:45	Routers	         194.180.163.196
Xiaomi Yi	                  199.0	  2017-04-02 13:54:30	Action Cameras	 19.56.252.66
iWatch 1 38mm	                  499.0	  2017-04-04 12:50:05	Smart watches	 40.159.224.180
LG 24	                          539.0	  2017-04-03 23:44:56	Monitors	 199.195.255.172
MakBook	                          849.0	  2017-04-07 03:24:21	Laptops	         153.113.215.33
Canon EOS 5D Mark IV	          1209.0  2017-04-07 22:19:46	Cameras	         234.65.207.61
```
