## WebClient

_**WebClient**_ is an interface representing the main entry point for performing web requests.

It was created as part of the Spring Web Reactive module, and will be replacing the classic _**RestTemplate**_ in these
scenarios. In addition, the new client is a reactive, non-blocking solution that works over the HTTP/1.1 protocol.

It's important to note that even though it is, in fact, a non-blocking client and it belongs to the _**spring-webflux**_
library, the solution offers support for both synchronous and asynchronous operations, making it suitable also for
applications running on a Servlet Stack.

A Flux represents a stream of elements. It’s a sequence that will asynchronously emit any number of items (0 or more) in
the future, before completing (either successfully or with an error).

A Mono is a specific but very common type of Flux: a Flux that will asynchronously emit either 0 or 1 results before it
completes.

# Useful links:

- https://www.baeldung.com/spring-5-webclient
- https://howtodoinjava.com/spring-webflux/webclient-get-post-example/#:~:text=%20Sending%20Requests%20%201%20Create%20WebClient.UriSpec%20reference,directly%20performs%20the%20HTTP%20request%20and...%20More%20