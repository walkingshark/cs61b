# Gitlet Design Document

**Name**:
## Designing
1. what data?
2. how to store data?
3. when to store?

## Classes and Data Structures

### Repository

#### Fields

1. staging area(add)
2. staging area(remove)
3. some commit tree
4. master pointer: points to the current commit of master branch
5. head pointer: points to the current commit of active branch
6. some blobs


### staging area(add)
#### staging an already exist file -> overwrite the older version
in .gitlet
#### how to know if a file already exists?
#### build a map: file name to current file sha1 id, and with id, find a version of
a file (blob)

#### Fields

1. 
2. Field 2

### blob
content of a file
maybe inside a folder name of its sha1 code so that it's easier to find


#### Fields

### commit
#### Fields
meta datas
2 strings represent parents
a map storing blobs





## Algorithms
### init 
a repo is never instantiated, instead a .gitlet dir is created
### Add
add a new blob
update staging area
### commit
use add and rm to update commit
rm: untrack a file in commit
1. staging area is cleared after commit
2. the commit just added become currrent commit and is added to the commit tree

## Persistence
### how to store data?
how to check if sth already read or not?
if it hasn't read or just clear
ans: for add and remove in staging area, they are first set to a initial map.
when doing operations like add and rm, first check whether add or rm map is equal to initial.
if so, read from file.


