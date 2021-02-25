import { RequestService } from './../../services/request.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Request } from 'src/app/models/request';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

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

  submitForm(): void {
    this.request.customer_id = this.validateForm.get('userID').value;
    this.request.location = this.validateForm.get('location').value;
    this.request.license_key = this.validateForm.get('license').value;

    if (this.validateForm.valid) {
      this.requestService.save(this.request).subscribe(() => {
        this.gotoInfoPage();
      });
    } else {
      for (const i in this.validateForm.controls) {
        this.validateForm.controls[i].markAsDirty();
        this.validateForm.controls[i].updateValueAndValidity();
      }
    }
  }

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private requestService: RequestService,
    private fb: FormBuilder
  ) {
    this.request = new Request();
  }

  ngOnInit(): void {
    this.validateForm = this.fb.group({
      userID: [null, [Validators.required]],
      location: [null, [Validators.required]],
      license: [null, [Validators.required]],
    });

    this.requestService.getLocationsAndLicences().subscribe((data) => {
      this.locations = data['locations'];
      this.licences = data['licences'];

      if (this.licences.length === 0) {
        alert('No licences available. Try again later.');
      }
    });
  }

  gotoInfoPage() {
    this.router.navigate(['']);
  }
}
