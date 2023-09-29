/*
 * Copyright (C) 2022 Google LLC. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.sample.cast.refplayer.utils

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.HurlStack
import kotlin.jvm.Synchronized
import android.graphics.Bitmap
import androidx.collection.LruCache
import com.android.volley.Cache
import com.android.volley.Network
import com.android.volley.toolbox.ImageLoader

class CustomVolleyRequest private constructor(context: Context) {
    private var requestQueue: RequestQueue?
    private var context: Context?
    val imageLoader: ImageLoader

    private fun getRequestQueue(): RequestQueue {
        if (requestQueue == null) {
            val cache: Cache = DiskBasedCache(context!!.cacheDir, 10 * 1024 * 1024)
            val network: Network = BasicNetwork(HurlStack())
            requestQueue = RequestQueue(cache, network)
            requestQueue!!.start()
        }
        return requestQueue!!
    }

    companion object {
        private var customVolleyRequest: CustomVolleyRequest? = null
        @JvmStatic
        @Synchronized
        fun getInstance(context: Context): CustomVolleyRequest? {
            if (customVolleyRequest == null) {
                customVolleyRequest = CustomVolleyRequest(context)
            }
            return customVolleyRequest
        }
    }

    init {
        this.context = context
        requestQueue = getRequestQueue()
        imageLoader = ImageLoader(requestQueue,
                object : ImageLoader.ImageCache {
                    private val cache = LruCache<String, Bitmap>(20)
                    override fun getBitmap(url: String): Bitmap? {
                        return cache[url]
                    }

                    override fun putBitmap(url: String, bitmap: Bitmap) {
                        cache.put(url, bitmap)
                    }
                })
    }
}