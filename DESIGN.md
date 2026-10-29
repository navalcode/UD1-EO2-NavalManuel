# Monument HTTP contract

The surface is the request, status and stored result. Native Markdown presents the route/result matrix and reading order, with English/Spanish counterparts. There is no frontend requiring a visual skin, generated imagery or scroll animation.

An absent monument must be observable as 404 instead of a 200/null body or a server exception. A POST with an existing identifier must not act like an update: it fails before touching the repository. Valid PUT retains the resource identifier, and deletion distinguishes a confirmed removal from an absent target. Existing fields and successful JSON shapes remain unchanged.

The six HTTP journeys use real Spring MVC/JPA and isolated H2, assert both responses and persistence effects, and preserve the original context smoke test. Test fixtures are fictional and image URLs are data rather than fetched resources. Original packages, startup seed, toolchain and history are retained. The check scope is local MockMvc/JPA; a hosted service or production API is not asserted.
