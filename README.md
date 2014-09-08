PipeTableFormatter
==================

Small IDEA plugin for formatting text blocks that represents a table delimited by pipe (|). For example, can be used to format JBehave examples table.

Thanks to Idris Ahmed ([idrisahmed251] (https://github.com/idrisahmed251)) for an idea to treat comma as delimiter too. This makes possible easily convert csv tables (e.g. exported from excel) to JBehave examples tables.  

Supported delimiters: pipe, comma, tab. When formatting any of supported delimiters are converted to pipe.

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

From Plugin Repository: http://plugins.jetbrains.com/plugin/7550

Usage
-----

1. Select text that should be formatted or just put caret somewhere inside a table.
2. Choose action Pipe Table Formatter in Code menu.
