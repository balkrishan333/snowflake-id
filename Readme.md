# Tasks

* Create SpringBoot Java project - **Done**
* Create database - **Done**
* Create business service - **Done**
* Create controller for Rest API - **Done**
* Write Repo for Database operations - **Done**
* Save ids to DB - **Done**
* Create docker file
* Create k8s manifest
* Deploy on EKS
* Write terraform to create AWS infra
* Create Angular UI
* Integrate Bootstrap CSS

# DB save performance Results

## Re-accessing stats -- Time = ID generation + save time
### Record size - 100k
* without reWriteBatchedInserts,without batch_size,with saveAll - 12347 ms
* with reWriteBatchedInserts,without batch_size,with saveAll    - 11830 ms
* with reWriteBatchedInserts,with batch_size(100k),with saveAll - 6326 ms
* with reWriteBatchedInserts,with batch_size(30k),with saveAll  - 7470 ms
* with copy, with reWriteBatchedInserts                         - 1326 ms
### Record size - 500k
* without reWriteBatchedInserts,without batch_size,with saveAll - 66530 ms
* with reWriteBatchedInserts,without batch_size,with saveAll    - 61111 ms
* with reWriteBatchedInserts,with batch_size(100k),with saveAll - 19750 ms
* with reWriteBatchedInserts,with batch_size(30k),with saveAll  - 17261 ms
* with copy, with reWriteBatchedInserts                         - 3482 ms
