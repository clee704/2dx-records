2dx-records
===========

2dx-records is a Java application developed in 2008 that extracts play records
from CS Beatmania IIDX save files. You can extract records from IIDX RED, HAPPY
SKY, DistorteD, and GOLD. Currently there's no plan to expand the list of
supported versions, as the author is not interested in this app anymore.

Java 6 or higher is required to run this app.

Visit [my blog](http://blog.clee.kr/bm2dx/records/) to view web pages created
with this app.


How to Use
----------

Disclaimer: This app is not easy to use because it was written when the author
did not know what a good application should be.

First, you need a save file in PSU format containing records to be extracted.
There are a number of ways to get it from your PlayStation 2 memory card. For
example, you can use Max Drive to get a .max file and PS2MemConv to convert it
to a .psu file. If you just want to try the app, there are the author's save
files in `test/name/lemonedo/iidx/record/resource` folder.

To build and run the app, you need Java 6 or higher and Apache Ant. Type `ant
release-files` to get an executable JAR file in `releases` folder. Doubleclick
the JAR file to run (Ignore the error message saying `BrowserLauncher2.jar` is
not found; something is broken but it does not affect the core functionality of
the app).

In the app, you can select a save file and export records as HTML (In
retrospect, if you should choose only one format, it should be JSON, XML, or
even CSV but absolutely not HTML, though it can be one of many formats. What
was the author thinking??). Finally, to view image files in the exported HTML
page, copy the images in `img` folder to the folder where the HTML page is.

Now you can open the page with your web browser. In the HTML page, click a
column header to sort records by that column.
