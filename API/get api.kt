val time = SystemDetailRepo.getInstance(this).getSyncTimeCategory()

        val url=DbContract.SERVER_URL+"/category/getcategories/"

             AuthorizedRequestHandler(
                Request.Method.GET,url,null,
                Response.Listener { response ->

                    val synctime=response.getString("synctime")
                    val moshi: Moshi = Moshi.Builder().build()
                    val type = Types.newParameterizedType(List::class.java, Category::class.java)
                    val adapter: JsonAdapter<List<Category>> = moshi.adapter(type)
                    val array = adapter.fromJson(response.getJSONArray("data").toString())?.toTypedArray()
                    val service_array = adapter.fromJson(response.getJSONArray("service_data").toString())?.toTypedArray()

                    if (array != null) {
                        CategoryRepo.getInstance(this).insertall(*array)
                    }
                    if (service_array != null) {
                        CategoryRepo.getInstance(this).insertall(*service_array)
                    }
                    SystemDetailRepo.getInstance(this).updateSyncStatusCategory(synctime)
                    syncItems()

                },

            this,resources,this
            ).send()
