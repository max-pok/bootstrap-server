import { AuthService } from './../../services/auth.service';
import { DataService } from './../../services/data.service';
import { Component, OnInit } from '@angular/core';
import { interval, timer } from 'rxjs';
import { Client } from 'src/app/models/client';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzConfigService } from 'ng-zorro-antd/core/config';

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
    public authService: AuthService,
    private message: NzMessageService
  ) {}

  ngOnInit(): void {
    const source = timer(0, 3000);

    source.subscribe((v) => {
      this.getData();
      this.checkForUpdate();
    });
  }

  /* Gets clent data from backend */
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
      });
  }

  checkForUpdate() {
    if (this.expiredConnections.length > this.prevList.length) {
      if (this.expiredConnections.length == 1) {
        this.message.create(
          'warning',
          `You have ${this.expiredConnections.length} expired license key. Please update it.`
        );
      } else {
        this.message.create(
          'warning',
          `You have an ${this.expiredConnections.length} expired license key. Please update them.`
        );
      }
    }

    this.prevList = this.expiredConnections;
  }

  resetAll() {
    this.dataService.resetAll().subscribe(() => {
      this.message.create('succsess', `Reset completed.`);
    });
  }
}
