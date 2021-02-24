package com.techkingsley.newsappcleanarchitecture.framework.ui.adapter

//class SearchResultAdapter(private val onNewsResultClicked: OnItemClickedListener) : RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder>() {
//
//    private var searchResults = emptyList<NewsNetworkEntity>()
//
//    interface OnItemClickedListener {
//        fun onSearchResultClicked(newsNetworkEntity: NewsNetworkEntity)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
//        val v = LayoutInflater.from(parent.context).inflate(R.layout.layout_news_search_result, parent, false)
//        val viewBinding = LayoutNewsSearchResultBinding.bind(v)
//        return SearchResultViewHolder(viewBinding)
//    }
//
//    override fun getItemCount(): Int {
//        return searchResults.size
//    }
//
//    fun setSearchResults(searchResults: List<NewsNetworkEntity>) {
//        this.searchResults = searchResults
//        notifyDataSetChanged()
//    }
//
//    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
//        holder.updateSearchResult(searchResults[position])
//    }
//
//    inner class SearchResultViewHolder(view: LayoutNewsSearchResultBinding) : RecyclerView.ViewHolder(view.root) {
//        private val viewBinding = view
//        fun updateSearchResult(newsNetworkEntity: NewsNetworkEntity) {
//            viewBinding.news = newsNetworkEntity
//            viewBinding.resultCallback = onNewsResultClicked
//            viewBinding.executePendingBindings()
//        }
//    }
//}