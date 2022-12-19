export class AppConfig {
    public static getAPIURI(): string {
      let environment: string;
      environment = window.location.hostname;
      let endPoint: string;
      switch (environment) {
        case 'localhost':
          endPoint = 'http://localhost:8082/api/';
          break;
        case 'development.returnation.nl':
          endPoint = 'https://obof01oudj.execute-api.us-east-1.amazonaws.com/UAT-user';
          break;
        default:
          endPoint = 'https://obof01oudj.execute-api.us-east-1.amazonaws.com/UAT-user';
      }
      return endPoint;
    }
  }