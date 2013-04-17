#Wiko
##Small Java library to converts Wiki markup language to HTML.

Wiko is a Java library that converts [Wiki Markup](http://www.mediawiki.org/wiki/Help:Formatting) language to HTML.<br>
It's based on code of [wiky.js](https://github.com/tanin47/wiky.js) and its buggy, so please use with care.

Supported Syntax
-------------------
* Heading (Levels 6 to 2): == Heading ==
* Links: [http://www.url.com|Name of URLs]
* images: [[File:http://www.url.com/image.png|alt=Alternative Text]]
* Horizontal line: ----
* Indentation: :
* Ordered bullet point: #
* Unordered bullet point: *
* Definition List: ; :

How to use it
-------------------
Example usage is can be found in `Main.java`.<br>
Its creates a file called *test.html* generated from the text in the `TestStrings.java`.<br>
*test.html* use `style.css` and `testPicture.png` included in the repository.

License
---------
[Do What The Fuck You Want To Public License] (http://sam.zoy.org/wtfpl/)
