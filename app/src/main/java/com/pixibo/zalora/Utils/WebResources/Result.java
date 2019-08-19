package com.pixibo.zalora.Utils.WebResources;


import com.pixibo.zalora.Utils.Utils;

public interface Result {
    void getWebResponse(String result, Utils.TYPE type, int statusCode);
}
