(ns java-time-dte.install
  (:require [datomic-type-extensions.types :as types])
  (:import [java.time DayOfWeek Duration Instant LocalDate LocalDateTime LocalTime Month MonthDay OffsetDateTime OffsetTime Period Year YearMonth ZonedDateTime ZoneId ZoneOffset]
           [java.time.temporal ChronoField ChronoUnit]))

(defmacro define-dte [id backing-type serialize-sig serialize-body deserialize-sig deserialize-body]
  `(do
     (defmethod types/get-backing-datomic-type ~id [_#] ~backing-type)
     (defmethod types/serialize ~id [_# ~@serialize-sig] ~serialize-body)
     (defmethod types/deserialize ~id [_# ~@deserialize-sig] ~deserialize-body)))

(define-dte :java.time/duration :db.type/string
  [^Duration this] (.toString this)
  [^String s] (Duration/parse s))

(define-dte :java.time/instant :db.type/instant
  [^Instant this] (java.util.Date/from this)
  [^java.util.Date inst] (Instant/ofEpochMilli (.getTime inst)))

(define-dte :java.time/local-date :db.type/string
  [^LocalDate this] (.toString this)
  [^String s] (LocalDate/parse s))

(define-dte :java.time/local-date-time :db.type/string
  [^LocalDateTime this] (.toString this)
  [^String s] (LocalDateTime/parse s))

(define-dte :java.time/local-time :db.type/string
  [^LocalTime this] (.toString this)
  [^String s] (LocalTime/parse s))

(define-dte :java.time/month-day :db.type/string
  [^MonthDay this] (.toString this)
  [^String s] (MonthDay/parse s))

(define-dte :java.time/offset-date-time :db.type/string
  [^OffsetDateTime this] (.toString this)
  [^String s] (OffsetDateTime/parse s))

(define-dte :java.time/offset-time :db.type/string
  [^OffsetTime this] (.toString this)
  [^String s] (OffsetTime/parse s))

(define-dte :java.time/period :db.type/string
  [^Period this] (.toString this)
  [^String s] (Period/parse s))

(define-dte :java.time/year :db.type/string
  [^Year this] (.toString this)
  [^String s] (Year/parse s))

(define-dte :java.time/year-month :db.type/string
  [^YearMonth this] (.toString this)
  [^String s] (YearMonth/parse s))

(define-dte :java.time/zoned-date-time :db.type/string
  [^ZonedDateTime this] (.toString this)
  [^String s] (ZonedDateTime/parse s))

(define-dte :java.time/zone-id :db.type/string
  [^ZoneId this] (.toString this)
  [^String s] (ZoneId/of s))

(define-dte :java.time/zone-offset :db.type/string
  [^ZoneOffset this] (.toString this)
  [^String s] (ZoneOffset/of s))

;; enums

(defmacro define-enum [id class enum-map]
  (let [lookup (gensym)
        reverse-lookup (gensym)]
    `(do
       (def ~lookup ~enum-map)

       (def ~reverse-lookup
         (into {} (map (juxt second first) ~lookup)))

       (define-dte ~id :db.type/keyword
         [this#] (or (~reverse-lookup this#)
                     (throw (Exception. (str "Unknown constant " this# " for enum " ~(str class)))))
         [kw#] (or (~lookup kw#)
                   (throw (Exception. (str kw# " is not a constant in enum " ~(str class)))))))))

(define-enum :java.time/chrono-unit ChronoUnit
  {:centuries ChronoUnit/CENTURIES
   :days ChronoUnit/DAYS
   :decades ChronoUnit/DECADES
   :eras ChronoUnit/ERAS
   :forever ChronoUnit/FOREVER
   :half-days ChronoUnit/HALF_DAYS
   :hours ChronoUnit/HOURS
   :micros ChronoUnit/MICROS
   :millennia ChronoUnit/MILLENNIA
   :millis ChronoUnit/MILLIS
   :minutes ChronoUnit/MINUTES
   :months ChronoUnit/MONTHS
   :nanos ChronoUnit/NANOS
   :seconds ChronoUnit/SECONDS
   :weeks ChronoUnit/WEEKS
   :years ChronoUnit/YEARS})

(define-enum :java.time/chrono-field ChronoField
  {:aligned-day-of-week-in-month ChronoField/ALIGNED_DAY_OF_WEEK_IN_MONTH
   :aligned-day-of-week-in-year ChronoField/ALIGNED_DAY_OF_WEEK_IN_YEAR
   :aligned-week-of-month ChronoField/ALIGNED_WEEK_OF_MONTH
   :aligned-week-of-year ChronoField/ALIGNED_WEEK_OF_YEAR
   :ampm-of-day ChronoField/AMPM_OF_DAY
   :clock-hour-of-ampm ChronoField/CLOCK_HOUR_OF_AMPM
   :clock-hour-of-day ChronoField/CLOCK_HOUR_OF_DAY
   :day-of-month ChronoField/DAY_OF_MONTH
   :day-of-week ChronoField/DAY_OF_WEEK
   :day-of-year ChronoField/DAY_OF_YEAR
   :epoch-day ChronoField/EPOCH_DAY
   :era ChronoField/ERA
   :hour-of-ampm ChronoField/HOUR_OF_AMPM
   :hour-of-day ChronoField/HOUR_OF_DAY
   :instant-seconds ChronoField/INSTANT_SECONDS
   :micro-of-day ChronoField/MICRO_OF_DAY
   :micro-of-second ChronoField/MICRO_OF_SECOND
   :milli-of-day ChronoField/MILLI_OF_DAY
   :milli-of-second ChronoField/MILLI_OF_SECOND
   :minute-of-day ChronoField/MINUTE_OF_DAY
   :minute-of-hour ChronoField/MINUTE_OF_HOUR
   :month-of-year ChronoField/MONTH_OF_YEAR
   :nano-of-day ChronoField/NANO_OF_DAY
   :nano-of-second ChronoField/NANO_OF_SECOND
   :offset-seconds ChronoField/OFFSET_SECONDS
   :proleptic-month ChronoField/PROLEPTIC_MONTH
   :second-of-day ChronoField/SECOND_OF_DAY
   :second-of-minute ChronoField/SECOND_OF_MINUTE
   :year ChronoField/YEAR
   :year-of-era  ChronoField/YEAR_OF_ERA})

(define-enum :java.time/month Month
  {:january Month/JANUARY
   :february Month/FEBRUARY
   :march Month/MARCH
   :april Month/APRIL
   :may Month/MAY
   :june Month/JUNE
   :july Month/JULY
   :august Month/AUGUST
   :september Month/SEPTEMBER
   :october Month/OCTOBER
   :november Month/NOVEMBER
   :december Month/DECEMBER})

(define-enum :java.time/day-of-week DayOfWeek
  {:monday DayOfWeek/MONDAY
   :tuesday DayOfWeek/TUESDAY
   :wednesday DayOfWeek/WEDNESDAY
   :thursday DayOfWeek/THURSDAY
   :friday DayOfWeek/FRIDAY
   :saturday DayOfWeek/SATURDAY
   :sunday DayOfWeek/SUNDAY})
