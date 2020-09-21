# blog-verse
This project creates a web app, connects it to local mysql db and uses dropwizard

This Project makes a really simple blog app, where a user can post a blog, the blog will consists of the following things:
<ul>
  <li> Title </li>
  <li> Subtitle </li>
  <li> Author </li>
  <li> Email </li>
  <li> Created At </li>
</ul>

But before the user can post any blog, they need to login to the server, well that has been taken care of by using a simplied token based authentication. Let's say that you want to sign up for this amazing `blog-verse`, all you need to do is go to `localhost:8080/createUser`, provide your details in the body part of the request, you can use Postman for thatm it comes quite handy.<br>
User model consists of following:
<ul>
  <li> Name </li>
  <li> Email </li>
  <li> Password </li>
</ul>

<b>Note</b>: there are some fields that are not exposed to the outer world, like `tokens`, `userId` and `contentId`.

Clone this project `git clone git@github.com:confusedconsciousness/blog-verse.git`
I have used maven as my build tool, in order to build it open the terminal inside your editor and type `mvn clean install`

The above process will create a `.JAR` file inside the `target` directory, we need to run that `JAR` file, running that will require a `config.yml` file, it has to be setup such that it can communicate with your local mariadb mysql database. The `config.yml` has two shards initialized, note it is necessary to have atleast two shards, otherwise it will throw `IndexOutOfBoundError`. so make sure you create two databases, and provide the credentials in the `config.yml` file.

You can then run the web app by `java -jar target/path_to_.jar server config.yml`

Make sure to go throught the Resources to get an idea of the API endpoints, as well as where are they being mapped.
