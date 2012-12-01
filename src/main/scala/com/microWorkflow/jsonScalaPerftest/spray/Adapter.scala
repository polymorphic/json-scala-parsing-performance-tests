package com.microWorkflow.jsonScalaPerftest.spray

import com.microWorkflow.jsonScalaPerftest.TimeMeasurements
import spray.json._


/*
{
    "contributors": null,
    "coordinates": null,
    "created_at": "Mon Jun 27 21:45:46 +0000 2011",
    "entities": {
        "hashtags": [],
        "urls": [
            {
                "display_url": "mercynotes.com",
                "expanded_url": "http://www.mercynotes.com/",
                "indices": [
                    61,
                    80
                ],
                "url": "http://t.co/lKzLFOd"
            }
        ],
        "user_mentions": []
    },
    "favorited": false,
    "geo": null,
    "id": 85463859615379456,
    "id_str": "85463859615379456",
    "in_reply_to_screen_name": null,
    "in_reply_to_status_id": null,
    "in_reply_to_status_id_str": null,
    "in_reply_to_user_id": null,
    "in_reply_to_user_id_str": null,
    "place": null,
    "retweet_count": 0,
    "retweeted": false,
    "source": "web",
    "text": "Been watching Wimbledon? Check out new post Love and Tennis: http://t.co/lKzLFOd",
    "truncated": false,
    "user": {
        "contributors_enabled": false,
        "created_at": "Mon May 30 16:35:44 +0000 2011",
        "default_profile": true,
        "default_profile_image": false,
        "description": "",
        "favourites_count": 0,
        "follow_request_sent": null,
        "followers_count": 6,
        "following": null,
        "friends_count": 12,
        "geo_enabled": false,
        "id": 307978890,
        "id_str": "307978890",
        "is_translator": false,
        "lang": "en",
        "listed_count": 0,
        "location": "NC",
        "name": "Julie LaJoe",
        "notifications": null,
        "profile_background_color": "C0DEED",
        "profile_background_image_url": "http://a0.twimg.com/images/themes/theme1/bg.png",
        "profile_background_image_url_https": "https://si0.twimg.com/images/themes/theme1/bg.png",
        "profile_background_tile": false,
        "profile_image_url": "http://a0.twimg.com/profile_images/1375001769/JulieMNnew__2__normal.jpg",
        "profile_image_url_https": "https://si0.twimg.com/profile_images/1375001769/JulieMNnew__2__normal.jpg",
        "profile_link_color": "0084B4",
        "profile_sidebar_border_color": "C0DEED",
        "profile_sidebar_fill_color": "DDEEF6",
        "profile_text_color": "333333",
        "profile_use_background_image": true,
        "protected": false,
        "screen_name": "mercynotes",
        "show_all_inline_media": false,
        "statuses_count": 13,
        "time_zone": "Quito",
        "url": "http://mercynotes.com",
        "utc_offset": -18000,
        "verified": false
    }
}

 */

class SprayAdapter extends TimeMeasurements {

  def measure(json: String, iterations: Int) = {
    val startUserTime = getUserTime
    //implicit val formats = DefaultFormats

    val tweets = json.asJson
    /*
    val tweets = Json(json) match {
      case obj: JsonObject => ToObject[Tweet](obj)
      case array: JsonArray => //array.extract[List[Tweet]]
      case _ => List[Tweet]()
    }
    */
    val userTime = getUserTime - startUserTime
    userTime
  }

}
