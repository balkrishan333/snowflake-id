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

# Results

## Inserting large data in DB
* 100k records each in separate transaction - 2354918 ms
* 100k records each separately without transaction - 2803729 ms
* 100k records in batch of 100k - 10189 ms

# Re-accessing stats -- Time = ID generation + save time
## Record size - 100k
* without reWriteBatchedInserts,without batch_size,with saveAll - 12347 ms
* with reWriteBatchedInserts,without batch_size,with saveAll - 11830 ms
* with reWriteBatchedInserts,with batch_size(100k),with saveAll - 6326 ms
* with reWriteBatchedInserts,with batch_size(30k),with saveAll - 7470 ms
## Record size - 500k
* without reWriteBatchedInserts,without batch_size,with saveAll - 66530 ms
* with reWriteBatchedInserts,without batch_size,with saveAll - 61111 ms
* with reWriteBatchedInserts,with batch_size(100k),with saveAll - 19750 ms
* with reWriteBatchedInserts,with batch_size(30k),with saveAll - 17261 ms
