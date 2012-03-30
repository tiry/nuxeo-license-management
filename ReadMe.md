
# nuxeo-license-management

## Why this module

Although Nuxeo Platform is purely opensource and ditributed under LGPL Licenses, some people build commercial products on top of it and then may need to manage software licenses associated with usage restriction.
This module is a skeleton / sample that shows how a License managament service could be built and plugged inside Nuxeo infrastructure.

## Infrastructure integration

So far the infrastructure plugs are :

 - Security : so that user is associated to dynamic virtual groups based on the licenses that are granted to him

 - Http layer : so that you can control than the same license / login is not used by several concurrent http sessions

 - Startup : check Licenses status on startup and possibly stop the deployment of the server if needed

 - Periodic check : check Licenses status on a priodic basis and possibly stop the server if needed

 - Global expiration message : display a expiration message on all pages if liceses are about to expire

 - Admin center : add a new entry in admin center to manage the licenses

## Known limitations

This code is just a sample, by definition, Nuxeo people (or at least me) are not experts in software license management!

In addition keep in mind that your propietary code needs to include :

 - a check that the license manager service is indeed deployed (just add a <require> in your deployment-fragement)

 - a check that implementation of the service is the Genuine one :)
   ( you can do a check on the underlying NXRuntime Component class for that)

## Additional notes

It's up to you to define what happends if licenses validation does fails.
You may want to shutdown the server or to make it read only.

The current implementation does only log a WARN.




