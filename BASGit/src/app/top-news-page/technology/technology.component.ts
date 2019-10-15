import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-technology',
  templateUrl: './technology.component.html',
  styleUrls: ['./technology.component.css']
})
export class TechnologyComponent implements OnInit {
  topNews: any;
  newsSelection: String = "country=us&category=technology";

  imageStatus(imageJson) {
    if(imageJson) {
      return true;
    } else {
      return false;
    }
  }

  synopsisStatus(synopsisJson) {
    //this is for youtube stories that give a boilerplate synopsis that says nothing
    if(synopsisJson === "[[getSimpleString(data.title)]]\r\n[[getSimpleString(data.description)]]\r\n[[getSimpleString(data.videoCountText)]]") {
      return false;
    } else if(synopsisJson) {
      return true; 
    } else {
      return false;
    }
  }


  constructor(private http : HttpClient) { }
​
  ngOnInit() {
    let observable = this.http.get(`https://newsapi.org/v2/top-headlines?${this.newsSelection}&apiKey=596a0fc3564340dda0564acc17baae85`)
    observable.subscribe((result=>{
      this.topNews = result;
    }))
    
  }
}
