import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';

import { SessionService } from './session.service';
import {SessionInformation} from "../interfaces/sessionInformation.interface";

describe('SessionService', () => {
  let service: SessionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SessionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should emit isLogged status as true when logged in', (done) => {
    const mockSession: SessionInformation = {
      token: 'token',
      type: 'type',
      id: 1,
      username: 'username',
      firstName: 'firstName',
      lastName: 'lastName',
      admin: true,
    };

    service.$isLogged().subscribe((isLogged) => {
      if (isLogged) {
        expect(isLogged).toBe(true);
        done();
      }
    });

    service.logIn(mockSession);
  });

  it('should log in', () => {
    const mockSession: SessionInformation = {
      token: 'token',
      type: 'type',
      id: 1,
      username: 'username',
      firstName: 'firstName',
      lastName: 'lastName',
      admin: true,
    };

    service.logIn(mockSession);

    expect(service.isLogged).toBe(true);
    expect(service.sessionInformation).toEqual(mockSession);
  });

  it('should log out', () => {
    const mockSession: SessionInformation = {
      token: 'token',
      type: 'type',
      id: 1,
      username: 'username',
      firstName: 'firstName',
      lastName: 'lastName',
      admin: true,
    };

    service.logIn(mockSession);
    service.logOut();

    expect(service.isLogged).toBe(false);
    expect(service.sessionInformation).toBeUndefined();
  });
});
