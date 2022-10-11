package com.example.mysubmission_intermediate.Remote.Data

import com.example.mysubmission_intermediate.Api.ApiService
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mysubmission_intermediate.Api.StoryResponseItem

class StoryPagingSource(private val apiService: ApiService, private val token: String):
    PagingSource<Int, StoryResponseItem>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StoryResponseItem> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getAllStories(
                token = token,
                position,
                params.loadSize
            )
            val data = responseData.storyResponseItems

            LoadResult.Page(
                data = data,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (data.isNullOrEmpty()) null else position + 1
            )
        } catch (execption: Exception) {
            LoadResult.Error(execption)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, StoryResponseItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }


}
