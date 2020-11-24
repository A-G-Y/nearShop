package com.example.nearshop.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nearshop.R


class RecyclerViewFragment : Fragment() {

    private lateinit var currentLayoutManagerType: LayoutManagerType
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var dataset: Array<String>
    private lateinit var dataDist: Array<String>
    /*private lateinit var dataImage: Array<String>*/

    enum class LayoutManagerType { GRID_LAYOUT_MANAGER, LINEAR_LAYOUT_MANAGER }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize dataset, this data would usually come from a local content provider or
        // remote server.
        initDataset()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(
            R.layout.recycler_view_frag,
            container, false
        ).apply {
            tag =
                TAG
        }

        recyclerView = rootView.findViewById(R.id.recyclerView)


        // LinearLayoutManager is used here, this will layout the elements in a similar fashion
        // to the way ListView would layout elements. The RecyclerView.LayoutManager defines how
        // elements are laid out.
        layoutManager = LinearLayoutManager(activity)

        currentLayoutManagerType =
            LayoutManagerType.LINEAR_LAYOUT_MANAGER

        if (savedInstanceState != null) {
            // Restore saved layout manager type.
            currentLayoutManagerType = savedInstanceState
                .getSerializable(KEY_LAYOUT_MANAGER) as LayoutManagerType
        }
        setRecyclerViewLayoutManager(currentLayoutManagerType)

        recyclerView.adapter = CustomAdapter(dataset, dataDist)


        return rootView
    }

    private fun setRecyclerViewLayoutManager(layoutManagerType: LayoutManagerType) {
        var scrollPosition = 0

        // If a layout manager has already been set, get current scroll position.
        if (recyclerView.layoutManager != null) {
            scrollPosition = (recyclerView.layoutManager as LinearLayoutManager)
                .findFirstCompletelyVisibleItemPosition()
        }

        when (layoutManagerType) {
            LayoutManagerType.GRID_LAYOUT_MANAGER -> {
                layoutManager = GridLayoutManager(
                    activity,
                    SPAN_COUNT
                )
                currentLayoutManagerType =
                    LayoutManagerType.GRID_LAYOUT_MANAGER
            }
            LayoutManagerType.LINEAR_LAYOUT_MANAGER -> {
                layoutManager = LinearLayoutManager(activity)
                currentLayoutManagerType =
                    LayoutManagerType.LINEAR_LAYOUT_MANAGER
            }
        }

        with(recyclerView) {
            layoutManager = this@RecyclerViewFragment.layoutManager
            scrollToPosition(scrollPosition)
        }

    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {

        // Save currently selected layout manager.
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, currentLayoutManagerType)
        super.onSaveInstanceState(savedInstanceState)
    }

    /**
     * Generates Strings for RecyclerView's adapter. This data would usually come
     * from a local content provider or remote server.
     */
    private fun initDataset() {
        val name = arrayOf(
            "BioAuvergne",
            "Naturalia",
            "L'eauVive",
            "Le Panier Bio",
            "La Fabrique de Marie",
            "L'épicerie du Monde",
            "Grand Panier Bio",
            "Histoire de Graine",
            "Galaxy Diet",
            "Arbre de Vie"
        )
        val distance = arrayOf(
            "10",
            "58",
            "69",
            "153",
            "245",
            "390",
            "420",
            "486",
            "820",
            "1506"
        )
        val image = arrayOf(
            "a1",
            "a2",
            "a3",
            "a4",
            "a5",
            "a6",
            "a7",
            "a8",
            "a9",
            "a10"
        )
        dataset = Array(DATASET_COUNT, { i -> name[i] })
        dataDist = Array(DATASET_COUNT, { i -> distance[i] })
        /*dataImage = Array(DATASET_COUNT, { i -> image[i] })*/

    }

    companion object {
        private val TAG = "RecyclerViewFragment"
        private val KEY_LAYOUT_MANAGER = "layoutManager"
        private val SPAN_COUNT = 2
        private val DATASET_COUNT = 10
    }
}