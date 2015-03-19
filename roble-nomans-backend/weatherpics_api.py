'''
Created on Feb 10, 2015

@author: Seth
'''
import protorpc
import endpoints
from models import Weatherpic

import main

@endpoints.api(name="weatherpics", version="v1", description="Weatherpics API")
class WeatherpicsApi(protorpc.remote.Service):
    '''Endpoints API for insert , list, and delete'''
    @Weatherpic.method(name="weatherpic.insert", path="weatherpic/insert", http_method="POST")
    def weatherpic_insert(self, request):
        #use field not method
        if request.from_datastore:
            my_pic= request
        else:
            my_pic= Weatherpic(parent= main.PARENT_KEY, caption= request.caption, image_url=request.image_url)
        my_pic.put()                                
        return my_pic;
  
    @Weatherpic.query_method(name="weatherpic.list", path="weatherpic/list", http_method="GET", 
                             query_fields=("limit", "order", "pageToken"))
    def weatherpic_list(self, query):
        return query
      
    #the trailing comma forces the request_fields to be a tuple
    @Weatherpic.method(name="weatherpic.delete", path="weatherpic/delete/{entityKey}", 
                       http_method="DELETE", request_fields=("entityKey",))
    def weatherpic_delete(self, request):
        if not request.from_datastore:
            raise endpoints.NotFoundException("Item to delete not found!")
        request.key.delete()
        return Weatherpic(caption='Deleted')
     
app = endpoints.api_server([WeatherpicsApi], restricted=False)    
        