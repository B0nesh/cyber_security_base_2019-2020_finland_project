### :uk: Project Description :uk:
This project comes from a course I am doing at the University of Helsinki about cybersecurity, the project description is as such:
> In the first course project, your task is to create a web application that has at least five different flaws from the [OWASP top ten list](https://www.owasp.org/images/7/72/OWASP_Top_10-2017_%28en%29.pdf.pdf).

### :es: Descripción del proyecto :es:
Este proyecto es parte de un curso que estoy haciendo en la Universidad de Helsinki sobre ciberseguridad, en este proyecto nos piden:
> En el primer proyecto del curso, tu objetivo es crear una aplicación web que tenga al menos cinco vulnerabilidades que aparecen en la [OWASP top ten list](https://www.owasp.org/images/7/72/OWASP_Top_10-2017_%28en%29.pdf.pdf)

# Installation
Just open the project with netbeans as we did in the course.

### FLAW 1 (SQL INJECTION)

#### Description of flaw 1
Visiting the index page (`127.0.0.1:8080`), we can see that there's a login form which is used to access to the `/logged` page,
where we will get our username and password printed if it exists in the database, if not, we will not receive any kind of text.
	
What are we going to exploit here, is the h2 database, which is integrated in spring framework, when we send the form using the login
button, there are two parameters being passed into the `/logged` template, `username` and `password`, the query in our web app is done this way.

`"SELECT * FROM users WHERE lower(USERNAME) = lower('" + username + "') AND PASSWORD = '" + password + "' ;"`
	
This let us insert an OR clause just after the password by adding a '
For example if we pass in password `' OR '1' = '1`
The query would be like this `"SELECT * FROM users WHERE lower(USERNAME) = lower(username) AND PASSWORD = password OR '1' = '1'`, giving us all the database data.

#### How to fix it

For fixing this, we would need to use parameterized queries, also we could use some LIMIT control queries to avoid mass leakaged in case of successful injection.


### FLAW 2 (BROKEN AUTHENTIFICATION):

#### Description of flaw 2
This flaw is pretty common the real world, a lot of users usually don't change the default passwords or they just use too easy to guess passwords which can be easily cracked
using brute force with dictionary attacks.

We can check that our website is also vulnerable on the authentification side, as we have checked with our SQL injection right before, there's an "admin" account with the default "admin" password, and sergio has 123456 as password
which would only take few seconds to crack.

#### How to fix it
First of all, we should remove all default passwords, for example the one being attached to admin, and implementing a strong one for it.
Second, we should implement some weak-password checks, such as testing them against a list of the top worst passwords.

We could also limit the ammount of failed login attempts, in order to spoil bruteforce attacks.

In addition, another clever idea could be implementing multi-factor authentification which would prevent 95% of the attacks we could experience in this field, but the con is that it's not always possible to do so.


### FLAW 3 (SENSITIVE DATA EXPOSURE):

#### Description of flaw 3
The flaw here is that some some critical data is not encrypted, being visible as plain-text data, for example we have the GET parameters being shown in the URL, when we use the login form we can check that our username and password is being shown there.
Also, we have checked in our SQL injection attack, that the passwords are not encrypted, making the attacker's life far easier

#### How to fix it
First of all we have to classify the data we are processing, to accord to the laws and business needs, is the necessary to store this data? can we make it more secure?

We have to know which data is required to protect and which not, which can be shown to public and which has to be privated.

If we don't need the data in the future, the best action is to delete it, data that is not retained cannot be stolen.

We have to encrypt all sensitie data at rest, using up-to-date and strong standard algorithms, protocols and keys....

Also important, using proper key management, if we lose the keys, we will lose the data.

We should use HTTPS instead of HTTP.

Don't cache responses that containt sensitive data.

Store passwords using strong adaptative and salted hashing functions with a work factor.

### FLAW 4 (BROKEN ACCESS CONTROL):
	
#### Description of flaw 4
In this flaw, we can access http://127.0.0.1:8080/admin without any kind of authentification, we shouldn't be able to enter the admin zone just by writing the url on the browser, there should be some kind of check.

#### How to fix it
We should deny any resource by default, with the exception of public resources, this way if we forget about some resources, it could stop some leaking.

Another thing we could do is hiding the web server directory listing, this way we can avoid to have people trying to guess some reserved stuff in our web.

Also, we should implement a fully functional user system, where each role is clearly defined and which resources of the page has access to.

We can also log access control failures, alerting us when appropiate.

### FLAW 5 (CROSS-SITE SCRIPTING "XSS"):

#### Description of flaw 5
There are 3 types of XSS attacks(Reflected XSS, Stored XSS and DOM XSS), this one is of the type Reflected XSS.

On the index page(`127.0.0.1:8080`), we have a submit form, asking for our name, if we write "John", we will me moved to a /greeting page where
a message "Hello John" will be displayed, if we write `<script>alert('Pop')</script>`, we can execute arbitrary code in the browser.

This is because we are hot using th:text, but th:utext instead, which doesn't format the text.

#### How to fix it
First thing, making sure that in our thymeleaft we're using th:text, also we can use frameworks that automatically escape XSS by design.

Another thing we can do is escape untrusted HTTP request data based on the context in the HTML output.
