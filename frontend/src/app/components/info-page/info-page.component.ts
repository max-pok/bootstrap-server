import { AuthService } from './../../services/auth.service';
import { DataService } from './../../services/data.service';
import { Component, OnInit } from '@angular/core';
import { interval } from 'rxjs';
import { Client } from 'src/app/models/client';
import { NzMessageService } from 'ng-zorro-antd/message';

@Component({
  selector: 'app-info-page',
  templateUrl: './info-page.component.html',
  styleUrls: ['./info-page.component.css'],
})
export class InfoPageComponent implements OnInit {
  listOfData;
  activeConnections: Client[] = [];
  pandingConnections: Client[] = [];
  expiredConnections: Client[] = [];
  prevList: Client[] = [];

  constructor(
    private dataService: DataService,
    private authService: AuthService,
    private message: NzMessageService
  ) {}

  ngOnInit(): void {
    this.initData();

    interval(3000).subscribe((x) => {
      this.getData();
    });
  }

  getData(): void {
    this.dataService
      .getClientDataById(this.authService.user_id)
      .subscribe((data) => {
        this.listOfData = data;

        this.activeConnections = this.listOfData.filter((client) => {
          return client.server_id !== '' && client.license_expiration_time > 0;
        });

        this.pandingConnections = this.listOfData.filter((client) => {
          return (
            client.server_id === '' && client.license_expiration_time !== 0
          );
        });

        this.expiredConnections = this.listOfData.filter((client) => {
          return client.license_expiration_time === 0;
        });

        if (this.expiredConnections.length > this.prevList.length) {
          this.message.create(
            'warning',
            `You have an expired license key. Please update it.`
          );
        }

        this.prevList = this.expiredConnections;
      });
  }

  initData() {
    this.dataService
      .getClientDataById(this.authService.user_id)
      .subscribe((data) => {
        this.listOfData = data;

        this.activeConnections = this.listOfData.filter((client) => {
          return client.server_id !== '' && client.license_expiration_time > 0;
        });

        this.pandingConnections = this.listOfData.filter((client) => {
          return (
            client.server_id === '' && client.license_expiration_time !== 0
          );
        });

        this.expiredConnections = this.listOfData.filter((client) => {
          return client.license_expiration_time === 0;
        });

        if (this.expiredConnections.length > 0) {
          // this.message.create('warning', `Update your expired license keys`);
        }
      });
  }

  resetAll() {
    this.dataService.resetAll().subscribe(() => {
      this.message.create('succsess', `Reset completed.`);
    });
  }
}
