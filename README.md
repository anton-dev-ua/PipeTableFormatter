PipeTableFormatter
==================

Small plugin for Intellij IDEA to format table delimited by pipe "|". For example, can be used to format JBehave examples table.

Example
-------

text like:

<pre>
|Header 1|Header 2|
|val1|val2|
</pre>

is formatted to:

<pre>
| Header 1 | Header 2 |
| val1     | val2     |
</pre>

Installation
-----------

1. Download jar file with plugin: https://raw.githubusercontent.com/anton-dev-ua/PipeTableFormatter/master/PipeTableFormatter.jar
2. Go to Preferences/Plugins
3. Choose "Install plugin from disk"
4. Select downloaded file
5. restart IDEA

Usage
-----

1. Select text that should formatted;
2. Choose action Pipe Table Formatter in menu Code
