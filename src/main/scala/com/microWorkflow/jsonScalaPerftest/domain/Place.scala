package com.microWorkflow.jsonScalaPerftest.domain

case class Aspect(rating: Int, _type: String)
case class Review(aspects: Seq[Aspect], author_name: String, author_url: Option[String], text: String, time: Long)
case class Photo(height: Int, width: Int, html_attributions: Seq[String], photo_reference: String)
case class When(day: Int, time: String)
case class Period(open: When, close: When)
case class OpeningHours(open_now: Boolean, periods: Option[Seq[Period]])
case class Location(lat: BigDecimal, lng: BigDecimal)
case class Geometry(location: Location)
case class AddressComponent(long_name: String, short_name: String, types: Seq[String])
case class Place( address_components: Seq[AddressComponent]
                , formatted_address: Option[String]
                , formatted_phone_number: Option[String]
                , geometry: Geometry
                , icon: Option[String]
                , id: String
                , name: String
                , international_phone_number: Option[String]
                , opening_hours: Option[OpeningHours]
                , photos: Option[Seq[Photo]]
                , reference: String
                , reviews: Option[Seq[Review]]
                )

