describe('Register spec', () => {
  beforeEach(() => {
    cy.visit('/login');
  });
  it('should go to register page when register is clicked', () => {
    cy.contains('Register').click();

    cy.url().should('include', '/register');
  });
  it('Register successfull', () => {
    cy.visit('/register')

    cy.intercept('POST', '/api/auth/register', {
      body: {
        id: 1,
        username: 'userName',
        firstName: 'firstName',
        lastName: 'lastName',
        admin: true
      },
    })

    cy.intercept(
      {
        method: 'GET',
        url: '/api/session',
      },
      []).as('session')

    cy.get('input[formControlName=firstName]').type("testing")
    cy.get('input[formControlName=lastName]').type("cypress")
    cy.get('input[formControlName=email]').type("test1@test.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

    cy.url().should('include', '/login')
  })

  it('should not register with bad credentials', () => {
    cy.visit('/register')
    cy.get('input[formControlName=firstName]').type("testing")
    cy.get('input[formControlName=lastName]').type("cypress")
    cy.get('input[formControlName=email]').type("ffefezze")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)
    cy.get('button[type=submit]').should('be.disabled');
  });
});
