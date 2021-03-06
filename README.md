﻿# Demo


# Install and launch MongoDB locally

With your project set up, you can install and launch the MongoDB database.

If you are using a Mac with homebrew, this is as simple as:

    brew install mongodb

With MacPorts:

    port install mongodb

For other systems with package management, such as Redhat, Ubuntu, Debian, CentOS, and Windows, see instructions at http://docs.mongodb.org/manual/installation/.

# Installation steps

mvn clean install (Java 8 required)

Setup Heroku: https://devcenter.heroku.com/articles/deploying-spring-boot-apps-to-heroku

Setup mlab with mongoDB: https://elements.heroku.com/addons/mongolab

# View currently configured MongoLab

https://www.mlab.com/databases/heroku_1qjc7193

user: heroku_1qjc7193
password: Mongodb1

# Configured Heroku API routes

Create Subscription Notification: https://stormy-ridge-70141.herokuapp.com/subscription/create?url={eventUrl}

Change Subscription Notification: https://stormy-ridge-70141.herokuapp.com/subscription/change?url={eventUrl}

Cancel Subscription Notification: https://stormy-ridge-70141.herokuapp.com/subscription/cancel?url={eventUrl}

Notice Subscription Notification: https://stormy-ridge-70141.herokuapp.com/subscription/notice?url={eventUrl}

Assign user notification: https://stormy-ridge-70141.herokuapp.com/user/assign?url={eventUrl}

Unassign user notification: https://stormy-ridge-70141.herokuapp.com/user/unassign?url={eventUrl}

# Helper routes to view repositories

accounts: https://stormy-ridge-70141.herokuapp.com/accounts

users: https://stormy-ridge-70141.herokuapp.com/users

# Technologies used

Jackson, Java 8, MongoDB, Spring-boot, Maven, Wildfly, etc.

