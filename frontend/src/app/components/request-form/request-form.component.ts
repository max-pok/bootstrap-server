import { AuthService } from './../../services/auth.service';
import { RequestService } from './../../services/request.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Request } from 'src/app/models/request';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NzMessageService } from 'ng-zorro-antd/message';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'request-form',
  templateUrl: './request-form.component.html',
  styleUrls: ['./request-form.component.css'],
})
export class RequestFormComponent implements OnInit {
  request: Request;
  validateForm!: FormGroup;

  selectedValue = null;

  locations = [];
  licences = [];

  constructor(
    private router: Router,
    private requestService: RequestService,
    private fb: FormBuilder,
    private auth: AuthService,
    private toastr: ToastrService
  ) {
    this.request = new Request();
  }

  ngOnInit(): void {
    this.validateForm = this.fb.group({
      userID: [this.auth.user_id, [Validators.required]],
      location: [null, [Validators.required]],
      license: [null, [Validators.required]],
    });

    this.requestService.getLocationsAndLicences().subscribe((data) => {
      this.locations = data['locations'];
      this.licences = data['licences'];

      if (this.licences.length === 0) {
        this.toastr.error(`No licences available. Try again later.`);
      }
    });
  }

  submitForm(): void {
    this.request.customer_id = this.validateForm.get('userID').value;
    this.request.location = this.validateForm.get('location').value;
    this.request.license_key = this.validateForm.get('license').value;

    if (this.validateForm.valid) {
      this.requestService.request(this.request).subscribe(() => {
        this.requestService.getResponse(this.request).subscribe((response) => {
          if (response !== '') {
            this.toastr.info('', response.toString());
          }
        });
      });
    } else {
      for (const i in this.validateForm.controls) {
        this.validateForm.controls[i].markAsDirty();
        this.validateForm.controls[i].updateValueAndValidity();
      }
    }
  }

  gotoInfoPage() {
    this.router.navigate(['']);
  }
}
