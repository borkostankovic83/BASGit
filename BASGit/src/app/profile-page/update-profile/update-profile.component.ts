import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { NgForm } from '@angular/forms'
import { Router } from '@angular/router';

@Component({
  selector: 'app-update-profile',
  templateUrl: './update-profile.component.html',
  styleUrls: ['./update-profile.component.css']
})
export class UpdateProfileComponent implements OnInit {
  
  loggedInUser: any;

  constructor(private http: HttpClient,
    private router: Router) { }

  onSubmit(f: NgForm) {
    this.http.post("http://ec2-52-90-209-187.compute-1.amazonaws.com:5555/BASGit/users/profile/update", JSON.stringify(f.value)).subscribe(res=>{
                 //here you received the response of your post
                 console.log(res);
                 //you can do asomething, like
           })
    console.log(f.value);  // { first: '', last: '' }
    console.log(f.valid);  // false
    this.router.navigateByUrl('/profile/profilepage');
  }

  ngOnInit() {
    let observable = this.http.get('http://ec2-52-90-209-187.compute-1.amazonaws.com:5555/BASGit/users/current')
    observable.subscribe((result => {
      this.loggedInUser = result;
    }))
  }

}
