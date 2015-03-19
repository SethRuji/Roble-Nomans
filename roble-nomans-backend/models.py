from endpoints_proto_datastore.ndb.model import EndpointsModel
from google.appengine.ext import ndb

# TODO: Convert this non-Endpoints version to use Endpoint Models!


class Weatherpic(EndpointsModel):
    """ Model to store a WeatherPic. """
    _message_fields_schema= ("entityKey", "image_url", "caption", "last_touch_date_time")
    image_url = ndb.StringProperty()
    caption = ndb.StringProperty()
    last_touch_date_time = ndb.DateTimeProperty(auto_now=True)
