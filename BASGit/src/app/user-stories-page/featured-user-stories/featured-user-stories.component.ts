import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-featured-user-stories',
  templateUrl: './featured-user-stories.component.html',
  styleUrls: ['./featured-user-stories.component.css']
})
export class FeaturedUserStoriesComponent implements OnInit {

  cat: any;
  userNews: any;

  constructor(private http : HttpClient) { }

  getRandomCatPicture() {
    let catobservable = this.http.get('http://aws.random.cat/meow')
    catobservable.subscribe((res => {
      this.cat = res;
      return this.cat.file;
    }))
  }

  ngOnInit() {
    let observable = this.http.get('http://ec2-52-90-209-187.compute-1.amazonaws.com:5555/BASGit/stories/featured')
    observable.subscribe((result => {
      this.userNews = result;
    }))
  }

}
