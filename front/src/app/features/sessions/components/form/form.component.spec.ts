import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import {FormBuilder, ReactiveFormsModule} from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import {MatSnackBar, MatSnackBarModule} from '@angular/material/snack-bar';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { SessionService } from 'src/app/services/session.service';
import { SessionApiService } from '../../services/session-api.service';

import { FormComponent } from './form.component';
import {TeacherService} from "../../../../services/teacher.service";
import {ActivatedRoute, Router} from "@angular/router";
import {of} from "rxjs";
import {Session} from "../../interfaces/session.interface";

describe('FormComponent', () => {
  let component: FormComponent;
  let fixture: ComponentFixture<FormComponent>;
  let route: ActivatedRoute;
  let matSnackBar: MatSnackBar;
  let sessionApiService: SessionApiService;
  let sessionService: SessionService;
  let teacherService: TeacherService;
  let router: Router;

  const mockSessionService = {
    sessionInformation: {
      admin: true
    }
  }

  const mockedSession: Session = {
    name: "Session to update",
    date: new Date(),
    teacher_id: 1,
    description: "La session mockée",
    users: []
  };

  beforeEach(async () => {
    route = { snapshot: { paramMap: { get: jest.fn().mockReturnValue('1'), has: jest.fn().mockReturnValue('1'),  getAll: jest.fn().mockReturnValue('1'), keys: jest.fn() } } } as any;
    matSnackBar = { open: jest.fn() } as any;
    sessionApiService = { detail: jest.fn().mockReturnValue(of({})), create: jest.fn(), update: jest.fn() } as any;
    sessionService = { sessionInformation: mockSessionService } as any;
    teacherService = { all: jest.fn().mockReturnValue(of([]))} as any;
    router = { navigate: jest.fn(), url: '' } as any;
    await TestBed.configureTestingModule({

      imports: [
        RouterTestingModule,
        HttpClientModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule,
        MatSnackBarModule,
        MatSelectModule,
        BrowserAnimationsModule
      ],
      providers: [
        { provide: ActivatedRoute, useValue: route},
        { provide: MatSnackBar, useValue: matSnackBar },
        { provide: SessionService, useValue: mockSessionService },
        { provide: SessionApiService, useValue: sessionApiService },
        { provide: TeacherService, useValue: teacherService },
        { provide: Router, useValue: router },
        FormBuilder
      ],
      declarations: [FormComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(FormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should redirect non-admin users', () => {
    mockSessionService.sessionInformation.admin = false;
    component.ngOnInit();
    fixture.detectChanges();
    expect(router.navigate).toHaveBeenCalledWith(['/sessions']);
  });

  it('should create a new session', () => {
    component.onUpdate = false;
    component.sessionForm?.setValue({name: 'name', date: '2024-10-25', teacher_id: '1', description: 'description'});
    jest.spyOn(sessionApiService, 'create').mockReturnValue(of(component.sessionForm?.value as Session));
    component.submit();
    fixture.detectChanges();

    expect(sessionApiService.create).toHaveBeenCalledWith({name: 'name', date: '2024-10-25', teacher_id: '1', description: 'description'});
    expect(matSnackBar.open).toHaveBeenCalledWith('Session created !', 'Close', { duration: 3000 });
    expect(router.navigate).toHaveBeenCalledWith(['sessions']);
  });

  it("should initialize the 'create' form", () => {

    expect(component.sessionForm).toBeTruthy();
    expect(component.sessionForm?.controls["name"].value).toBe("");
    expect(component.sessionForm?.controls["date"].value).toBe("");
    expect(component.sessionForm?.controls["teacher_id"].value).toBe("");
    expect(component.sessionForm?.controls["description"].value).toBe("");

  });

  it("should display 'creation' form", () => {

    const title = fixture.nativeElement.querySelector("h1");

    expect(title.textContent).toBe("Create session");

  });
});
