# Delphix Automation Framework

The Delphix Automation Framework (DAF) allows automating data management via APIs at scale. Easily automate Delphix Self Service actions in CI/CD Pipelines without being tied to any specific CI Software.

#### Table of Contents
1.  [Description](#description)
2.  [Installation](#installation)
    *   [The delphix.yaml file](#delphix-yaml)
3.  [Usage](#usage)
4.  [Links](#links)
5.  [Contribute](#contribute)
    *   [Code of conduct](#code-of-conduct)
    *   [Community Guidelines](#community-guidelines)
    *   [Contributor Agreement](#contributor-agreement)
6.  [Reporting Issues](#reporting-issues)
7.  [Statement of Support](#statement-of-support)
8.  [License](#license)

## <a id="description"></a>Description

DAF combines environment variables commonly available during CI/CD and data management as code in the `delphix.yaml` file to automate API calls to the Delphix Dynamic Data Platform. This allows for chain-able API calls that can be triggered during different stages of the CI/CD pipeline. The data state for different non-production environments is now visible through code configuration and that configuration is under version control.

## <a id="installation"></a>Installation

The tools and executable for DAF have been containerized with docker for convenience. Use the latest version by pulling the container below.

```bash
docker pull delphix/daf
```
### <a id="delphix-yaml"></a>The delphix.yaml file

The `delphix.yaml` file is the configuration file that defines the data management as code strategy for the project. Create a `delphix.yaml` file based on this guide: [Configure Delphix YAML](./configure-delphix-yaml.md)

## <a id="usage"></a>Usage

Create an `.env` file with the following values:
*   GIT_BRANCH=
*   DELPHIX_PASS=
*   DELPHIX_USER=
*   DELPHIX_ENGINE=
*   GIT_EVENT=
*   GIT_COMMIT=

Optionally, the `GIT_EVENT` can be set by a GitHub Webhook Payload file: `payload.json`.<br /><br />
Run the docker container with your project mounted as a volume and environment file instantiated.

```bash
docker run -v ${PWD}:/daf/app --env-file ${PWD}/.env delphix/daf
```

## <a id="links"></a>Links

*   [Creating GitHub Webhooks](https://developer.github.com/webhooks/creating/)
*   [Setting Docker Environment Variables](https://docs.docker.com/engine/reference/commandline/run/#set-environment-variables--e---env---env-file)

## <a id="contribute"></a>Contribute

1.  Fork the project.
2.  Make your bug fix or new feature.
3.  Add tests for your code.
4.  Send a pull request.

Contributions must be signed as `User Name <user@email.com>`. Make sure to [set up Git with user name and email address](https://git-scm.com/book/en/v2/Getting-Started-First-Time-Git-Setup). Bug fixes should branch from the current stable branch. New features should be based on the `master` branch.

#### <a id="code-of-conduct"></a>Code of Conduct

This project operates under the [Delphix Code of Conduct](https://delphix.github.io/code-of-conduct.html). By participating in this project you agree to abide by its terms.

#### <a id="contributor-agreement"></a>Contributor Agreement

All contributors are required to sign the Delphix Contributor agreement prior to contributing code to an open source repository. This process is handled automatically by [cla-assistant](https://cla-assistant.io/). Simply open a pull request and a bot will automatically check to see if you have signed the latest agreement. If not, you will be prompted to do so as part of the pull request process.


## <a id="reporting_issues"></a>Reporting Issues

Issues should be reported in the GitHub repo's issue tab. Include a link to it.

## <a id="statement-of-support"></a>Statement of Support

This software is provided as-is, without warranty of any kind or commercial support through Delphix. See the associated license for additional details. Questions, issues, feature requests, and contributions should be directed to the community as outlined in the [Delphix Community Guidelines](https://delphix.github.io/community-guidelines.html).

## <a id="license"></a>License

This is code is licensed under the Apache License 2.0. Full license is available [here](./LICENSE).
