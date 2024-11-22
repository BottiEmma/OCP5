import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { UserService } from './user.service';
import {Teacher} from "../interfaces/teacher.interface";
import {User} from "../interfaces/user.interface";

describe('UserService', () => {
  let service: UserService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[
        HttpClientModule, HttpClientTestingModule
      ]
    });
    service = TestBed.inject(UserService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get user by ID', () => {
    const mockUser: User = {
      id: 1,
      email: 'test@example.com',
      firstName: 'John',
      lastName: 'Doe',
      admin: false,
      password: 'password123',
      createdAt: new Date(),
      updatedAt: new Date(),
    };
    service.getById('1').subscribe((users) => {
      expect(users).toEqual(mockUser);
    });
    const req = httpTestingController.expectOne('api/user/1');
    expect(req.request.method).toBe('GET');
    req.flush(mockUser);
  });

  it('should delete a user by ID', () => {
    service.delete('1').subscribe((response) => {
      expect(response).toEqual({});
    });
    const req = httpTestingController.expectOne('api/user/1');
    expect(req.request.method).toBe('DELETE');
    req.flush({});
  });
});
