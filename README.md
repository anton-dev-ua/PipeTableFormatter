PipeTableFormatter
==================

Small IDEA plugin for formatting text blocks that represents a table delimited by pipe (|). For example, can be used to format JBehave examples table.

Thanks to Idris Ahmed ([idrisahmed251] (https://github.com/idrisahmed251)) and to Jesse Kuhnert ([jkuhnert] (https://github.com/jkuhnert)) for ideas to use comma and tab as delimeters.  

Features
--------

- Formats block of text that represents a table: width of a column becomes constant from top to bottom by adjusting it to most wide value in the column. 
- Supported delimiters: pipe, comma, tab.
- Any of supported delimiters, when formatting, are converted to pipe.
- If text is not selected explicitly - autodetects start and end of the table based on caret position.

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
