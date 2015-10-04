#Recruiting Task

## The story
At Tictail we organize bi-annual Demo Weeks where we hack on creative
projects. It’s a good way to get that side project going or validate
an idea for a new feature. One of the ideas for the upcoming Demo Week
is a new team directory with a great deal of *wow* factor. You’ll be
building this new app and the cms that powers it.

## Provided goodies
We provide a server with a REST API for creating new contact
resources. These contacts are essentially the employees of Tictail.

Here is a description of the contacts API that you’ll be using:

**GET** `/contacts`

Retrieve a list of all *Contacts*. This endpoint also supports
filtering on all *Contact* properties except `id`. Any item matching
all - if any - filters will be returned.
`/contacts?<contact_property>=<filter_value>`.

**POST** `/contacts`

Create a new *Contact* object. All *Contact* properties are required.

**GET** `/contacts/<string:id>`

Returns a contact - if any - with the given `id`.

**PUT** `/contacts/<string:id>`

Updates a contact - if any - with the given `id`. All properties are
required except `id`.

#### Contact Object
All properties are of type String:

`id` - Unique identifier

`first_name` First name

`last_name` Last name

`title` Role title

`color` Arbitrary HTML color code without # prefix

`image` URL to avatar

`location` Location as timezone https://en.wikipedia.org/wiki/Tz_database

`team` Function level team


## Getting started
Install dependencies:

Install virtualenv if necessary:
```
pip install virtualenv
```

Setup work environment
```
virtualenv env
source env/bin/activate
pip install -r requirements.txt
```

Run the API server:

```
python runserver.py
```

Fetch the list of contacts to verify that things are working:

```
curl http://127.0.0.1:5000/contacts
```

## What you need to do
1. You need to implement a client side admin system that talks to our
API where one could add, remove and update contacts.  The interface of
this admin system is completely up to you.

Depending on the role you're applying for the requirements are as follows:

**Frontend**
We would like you to write this admin system in React.js since that is our
frontend tool of choice.

**iOS**
You may write your code in Swift, Objective-C, or any mixture of the two.

**Android**
We would like you to write your code in Java using Android Studio.

You may use any third-party libraries you find suitable, but we care much more
about seeing your own code. A thoughtful implementation of the base
requirements is better than a few one-line integrations of whiz-bang features.

1. You need to implement an innovative presentation of the directory which
will partially be judged by it's *wow* factor and it's production readynes.

1. Document your design and thought process in `THOUGHTS.md`. Keep it short :-)

## Delivery

The application should be delivered as a `git` repository, preferably hosted on
GitHub.

If we need to do anything special to run your code (e.g. CocoaPods/Carthage
installations or Module dependencies), please note that in an accompanying
README text file.

## What we look for
1. **Quality & design:**
Imagine that your solution will be delivered to production as-is, and
maintained by your fellow engineers. What are the things you need to
consider and implement to make it production-quality (tests, code
quality, input validation, documentation, etc)?

1. **Polish:**
At Tictail we expect a great deal of polish. This task is no
exception. If there are animations, do they make sense? How does your
solution handle a flaky mobile network? Does your margins and paddings make
sense?

1. **Wow factor:**
Imagine that this app is the public directory of all Tictail employees. We
would really would like to make our best to impress the users of such a useful
app and to make them even more likely to join our team.

A good rule of thumb is to submit something that you’re proud of. Good luck!

*N.B: Your code is provided solely for the purposes of this exercise
and will not be used by Tictail beyond the scope of assessing this assignment.*

