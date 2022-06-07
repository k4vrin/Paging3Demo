# Paging library

One of the most common ways of displaying information to users is with lists. However, sometimes these lists offer just a small window into all the content available to the user. As the user scrolls through the information that is available, there is often the expectation that more data is fetched to supplement the information that has already been seen. Each time data is fetched, it needs to be efficient and seamless so that incremental loads don't detract from the user experience. Incremental loads also offer a performance benefit, as the app does not need to hold large amounts of data in memory at once.

This process of fetching information incrementally is called pagination, where each page corresponds to a chunk of data to be fetched. To request a page, the data source being paged through often requires a query which defines the information required.

### Core components of the Paging Library

The core components of the Paging library are as follows:

-   `PagingSource` - the base class for loading chunks of data for a specific page query. It is part of the data layer, and is typically exposed from a `DataSource` class and subsequently by the `Repository` for use in the `ViewModel`.

-   `PagingConfig` - a class that defines the parameters that determine paging behavior. This includes page size, whether placeholders are enabled, and so on.
-   `Pager` - a class responsible for producing the `PagingData` stream. It depends on the `PagingSource` to do this and should be created in the `ViewModel`.
-   `PagingData` - a container for paginated data. Each refresh of data will have a separate corresponding `PagingData` emission backed by its own `PagingSource`.

-   `PagingDataAdapter` - a `RecyclerView.Adapter` subclass that presents `PagingData` in a `RecyclerView`. The PagingDataAdapter can be connected to a Kotlin `Flow`, a `LiveData`, an `RxJava` Flowable, an RxJava `Observable`, or even a static list using factory methods. The `PagingDataAdapter` listens to internal `PagingData` loading events and efficiently updates the UI as pages are loaded.

## PagingSource

When implementing pagination, we want to be confident the following conditions are met:

-   Properly handling requests for the data from the UI, ensuring that multiple requests aren't triggered at the same time for the same query.

-   Keeping a manageable amount of retrieved data in memory.

-   Triggering requests to fetch more data to supplement the data we've already fetched.

We can achieve all this with a PagingSource. A PagingSource defines the source of data by specifying how to retrieve data in incremental chunks. The PagingData object then pulls data from the PagingSource in response to loading hints that are generated as the user scrolls in a RecyclerView.
