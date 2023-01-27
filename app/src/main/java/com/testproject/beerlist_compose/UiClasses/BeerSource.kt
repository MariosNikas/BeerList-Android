package com.testproject.beerlist_compose.UiClasses

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.testproject.beerlist_compose.data.ApiService
import com.testproject.beerlist_compose.domain.Beer
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class BeerSource @Inject constructor(
    private val apiService: ApiService,
    private var fromDate: String?,
    private var toDate: String?,
    private var searchMode: Boolean
) : PagingSource<Int, Beer>() {
    val beersPerPage = 25
    var page = 1
    override fun getRefreshKey(state: PagingState<Int, Beer>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Beer> {
        delay(2000)
        return try {
            if (!searchMode) {
                val nextPage = params.key ?: 1
                val beerList = apiService.fetchBeers(nextPage.toString())
                Log.d("fetchBeers", beerList.body().toString())
                LoadResult.Page(
                    data = beerList.body()!!,
                    prevKey = if (nextPage == 1) null else nextPage - 1,
                    nextKey = if (beerList.body()!!.isEmpty()) null else (beerList.body()!!
                        .last().id.toInt() / beersPerPage + 1)
                )
            } else {
                val nextPage = params.key ?: 1
                val beerList = apiService.searchBeers(fromDate, toDate, nextPage.toString())
                Log.d(
                    "searchBeers",
                    "${beerList.body()!!.size} ${fromDate} ${toDate} ${beerList.body().toString()}"
                )
                LoadResult.Page(
                    data = beerList.body()!!,
                    prevKey = if (nextPage == 1) null else nextPage - 1,
                    nextKey = if (beerList.body()!!.isEmpty()) null else (++page)
                )
            }
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}