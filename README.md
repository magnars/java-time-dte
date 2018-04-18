# Datomic type extensions for java.time

This Clojure library installs custom Datomic types for
[java.time](https://docs.oracle.com/javase/8/docs/api/java/time/package-summary.html).
This is not something Datomic supports, so use it with
[datomic-type-extensions](https://github.com/magnars/datomic-type-extensions).

## Install

Add `[java-time-dte "2018-04-18"]` to `:dependencies` in your `project.clj`.

You will also need `datomic-type-extensions` and `datomic` on the class path.

## Usage

This library lets you set `:dte/valueType` to these custom types:

```clj
:java.time/duration
:java.time/instant
:java.time/local-date
:java.time/local-date-time
:java.time/local-time
:java.time/month-day
:java.time/offset-date-time
:java.time/offset-time
:java.time/period
:java.time/year
:java.time/year-month
:java.time/zoned-date-time
:java.time/zone-id
:java.time/zone-offset
:java.time/chrono-unit
:java.time/chrono-field
:java.time/month
:java.time/day-of-week
```

Remember to require `java-time-dte.install` somewhere.

See [datomic-type-extensions](https://github.com/magnars/datomic-type-extensions) for usage.

## License

Copyright © Magnar Sveen, since 2018

Distributed under the Eclipse Public License, the same as Clojure.
