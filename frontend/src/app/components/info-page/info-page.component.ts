import { AuthService } from './../../services/auth.service';
import { DataService } from './../../services/data.service';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { interval, Subscription, timer } from 'rxjs';
import { Client } from 'src/app/models/client';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzConfigService } from 'ng-zorro-antd/core/config';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-info-page',
  templateUrl: './info-page.component.html',
  styleUrls: ['./info-page.component.css'],
})
export class InfoPageComponent implements OnInit, OnDestroy {
  listOfData: Client[] = [];
  activeConnections: Client[] = [];
  pandingConnections: Client[] = [];
  expiredConnections: Client[] = [];
  prevList: Client[] = [];
  subscription: Subscription;

  constructor(
    private dataService: DataService,
    public authService: AuthService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    const source = timer(0, 3000);

    this.subscription = source.subscribe((v) => {
      this.getData();
      this.checkForUpdate();
    });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
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
        this.toastr.warning(
          `You have ${this.expiredConnections.length} expired license key. Please update it.`
        );
      } else {
        this.toastr.warning(
          `You have an ${this.expiredConnections.length} expired license key. Please update them.`
        );
      }
    }

    this.prevList = this.expiredConnections;
  }

  resetAll() {
    this.dataService.resetAll().subscribe(() => {
      this.toastr.success(`Reset completed`);
    });
  }
}
