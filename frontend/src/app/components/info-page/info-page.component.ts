import { DataService } from './../../services/data.service';
import { Component, OnInit } from '@angular/core';
import { interval } from 'rxjs';
import { Client } from 'src/app/models/client';

@Component({
  selector: 'app-info-page',
  templateUrl: './info-page.component.html',
  styleUrls: ['./info-page.component.css'],
})
export class InfoPageComponent implements OnInit {
  listOfData;
  activeConnections;
  pandingConnections;
  expiredConnections: Client[];

  constructor(private dataService: DataService) {
    this.initData();

    interval(3000).subscribe((x) => {
      this.getData();
    });
  }

  ngOnInit(): void {}

  getData(): void {
    this.dataService.getClientDataById('319475513').subscribe((data) => {
      this.listOfData = data;

      this.activeConnections = this.listOfData.filter((client) => {
        return client.server_id !== '' && client.license_expiration_time > 0;
      });

      this.pandingConnections = this.listOfData.filter((client) => {
        return client.server_id === '';
      });

      this.expiredConnections = this.listOfData.filter((client) => {
        return client.license_expiration_time === 0;
      });
    });
  }

  initData() {
    this.dataService.getClientDataById('319475513').subscribe((data) => {
      this.listOfData = data;

      this.activeConnections = this.listOfData.filter((client) => {
        return client.server_id !== '' && client.license_expiration_time > 0;
      });

      this.pandingConnections = this.listOfData.filter((client) => {
        return client.server_id === '';
      });

      this.expiredConnections = this.listOfData.filter((client) => {
        return client.license_expiration_time === 0;
      });

      if (this.expiredConnections.length > 0) {
        alert('update!');
      }
    });
  }
}
