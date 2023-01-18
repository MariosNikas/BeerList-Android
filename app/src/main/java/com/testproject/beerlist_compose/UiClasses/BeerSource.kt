package com.testproject.beerlist_compose.UiClasses

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.testproject.beerlist_compose.data.ApiService
import com.testproject.beerlist_compose.domain.Beer
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class BeerSource @Inject constructor(private  val apiService: ApiService): PagingSource<Int, Beer>()  {
    val beersPerPage =25
    override fun getRefreshKey(state: PagingState<Int, Beer>): Int?
    {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>):LoadResult<Int, Beer> {
        return try {
            val nextPage = params.key ?: 1
            val beerList =apiService.fetchBeers(nextPage.toString())
            LoadResult.Page(
                data = beerList.body()!!,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (beerList.body()!!.isEmpty()) null else (beerList.body()!!.last().id.toInt()/beersPerPage + 1)
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}