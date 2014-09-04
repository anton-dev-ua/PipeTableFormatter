PipeTableFormatter
==================

Small plugin for Intellij IDEA to format table delimited by pipe "|". For example, can be used to format JBehave examples table.

Thanks to Idris Ahmed ([idrisahmed251] (https://github.com/idrisahmed251)) for an idea to treat comma as delimiter too. This makes possible easily convert csv tables (e.g. exported from excel) to JBehave examples tables.  

Example
-------

text like:

<pre>
|Header 1|Header 2|
|val1|val2|
</pre>

or like:

<pre>
Header 1,Header 2
val1,val2
</pre>

is formatted to:

<pre>
| Header 1 | Header 2 |
| val1     | val2     |
</pre>

Installation
-----------

1. [Download jar file] (https://raw.githubusercontent.com/anton-dev-ua/PipeTableFormatter/master/PipeTableFormatter.jar) with plugin.
2. Go to Preferences/Plugins (Settings/Plugins).
3. Choose "Install plugin from disk".
4. Select downloaded file.
5. Restart IDEA.

Usage
-----

1. Select text that should be formatted or just put caret somewhere inside a table.
2. Choose action Pipe Table Formatter in Code menu.
