import { Client } from './../../models/client';
import { DataService } from './../../services/data.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-info-page',
  templateUrl: './info-page.component.html',
  styleUrls: ['./info-page.component.css'],
})
export class InfoPageComponent implements OnInit {
  listOfData;

  constructor(private dataService: DataService) {}

  ngOnInit(): void {
    this.dataService.getClientsData().subscribe((clients) => {
      this.listOfData = clients;
    });

    this.dataService.getClientDataById('319475513').subscribe((data) => {
      console.log(data);
    });
  }
}
