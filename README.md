Vertx WebSockets Test
=====================

An experimental project using [vertx](http://vertx.io/) as HTTP and WebSockets
servers, and HTML5 + jQuery + RaphaelJS as a client.

Server side
-----------
Provides simple HTTP server for serving static HTML + JS, and event-driven
WebSockets implementation of a very simple *"game"*. It's only purpose is to
present asynchronous capabilities of web technologies. It means that the only
thing the player can do is randomly spawn a rectangle at a random location by
connecting to a server, and remove it by disconnecting.

Client side
-----------
Creates SVG-rectangles tilemap to represent the *"game"* state. Player can see
his rectangle as well as the rectangles of other players which are dynamically
updated thanks to WebSockets connection. RaphaelJS library is being used in
order to easily manage SVG properties.

Disclaimer
----------
I have tried to keep clean code and approach, however keep in mind that this
project isn't meant to be state of art solution. Many bad designs might have
been applied due to a rush or curiosity.
