package com.example.mynotesapp

class Note{
    var nodeID:Int?=null
    var nodeTitle:String?=null
    var nodeDes:String?=null

    constructor(nodeID:Int,nodeTitle:String,nodeDes:String){
        this.nodeID=nodeID
        this.nodeTitle=nodeTitle
        this.nodeDes=nodeDes
    }
}