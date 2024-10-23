# Gitlet Design Document

**Name**:

## Classes and Data Structures

### Repository

#### Fields

1. staging area(add)
2. staging area(remove)
3. some commit tree
4. master pointer
5. head pointer
6. some blobs


### staging area(add)
staging an already exist file -> overwrite the older version
in .gitlet
#### Fields

1. pointer to blob(from itai's slide)
2. Field 2

### blob
content of a file
#### Fields

### commit
point to blobs.
#### Fields

## git structure
1. COMMIT_EDITMSG: last commit message
2. HEAD: ref: refs/heads/main (probably some paths)
3. config: some information
4. FETCH_HEAD:
5. ORIG_HEAD: a sha1 code
6. description: Unnamed repository; edit this file 'description' to name the repository.
(some message)
7. index: contains sth like sha1 codes for blobs and other stuff(however appears
a bunch of garbage)
8. hooks: some script
9. info: inside of it is :"exclude"(idk)
10. modules: has a module
refs: (folder) heads, remote, tags
    heads: main master (sha1 codes)
11. logs: head(a bunch of commit), refs->(head(main, master(ids), remotes)
12. objects: a bunch of sha1 folders(inside of it are 2 sha1), info, pack(those 2 idk)
## Algorithms


## Persistence

