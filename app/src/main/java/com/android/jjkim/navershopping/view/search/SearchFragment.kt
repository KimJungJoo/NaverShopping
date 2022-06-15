package com.android.jjkim.navershopping.view.search

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.jjkim.navershopping.R
import com.android.jjkim.navershopping.common.LogUtil
import com.android.jjkim.navershopping.databinding.FragmentSearchBinding
import com.android.jjkim.navershopping.view.base.BaseFragment
import com.android.jjkim.navershopping.service.repository.SearchRepository
import com.android.jjkim.navershopping.view.search.adapter.SearchResultAdapter
import com.android.jjkim.navershopping.viewmodel.search.SearchViewModel
import com.android.jjkim.navershopping.viewmodel.search.SearchViewModelFactory


class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    override var layoutId = R.layout.fragment_search
    lateinit var adapter: SearchResultAdapter

    //만일 Activity 범위 내에서 ViewModel을 초기화하려면 다음과 같이 작성해주면 된다.
    //ViewModelStoreOwner를 Acvitiy로 설정해 주는 것이다.
    override val viewModel: SearchViewModel by lazy {
        ViewModelProvider(requireActivity(), SearchViewModelFactory(SearchRepository())).get(
            SearchViewModel::class.java)
    }

    /*
     by viewModels를 사용해서 ViewModel을 초기화하면 ViewModel의 생명주기는 Fragment의 생명주기를 따르게 된다. (이는 by lazy 기반이다.)
     by lazy를 사용하면 초기화가 해당 객체가 최초로 사용되기 전까지 미뤄진다.
     override val viewModel: UserInfoViewModel by viewModels {
       UserInfoViewModelFactory(MyRepository())
    } */
    override fun init() {
        bindViewModel()
        setAdapter()
        setBtnListener()
        setObserver()
    }

    //ViewModel 초기화 되는 곳.
    private fun bindViewModel() {
        viewDataBinding.viewModel = viewModel
    }

    private fun setAdapter() {
        adapter = SearchResultAdapter(requireActivity())
        viewDataBinding.recycler.adapter = adapter
    }

    private fun setObserver() {
        viewModel.resSearch.observe(this, Observer{
            adapter.setItems(it.items)
        })

        viewModel.errorMessage.observe(this, Observer {
        })

        viewModel.getSearchShop("모자", 1, 15, "sim")
    }

    private fun changeFragment() {
//        FragmentTransitionManager()
//            .changeFragmentOnActivity(
//                requireActivity(),
//                R.id.main_container,
//                EditFragment(),
//                true
//            )
    }

    private fun setBtnListener() {
        viewDataBinding.btnSearch.setOnClickListener {

        }
    }
}